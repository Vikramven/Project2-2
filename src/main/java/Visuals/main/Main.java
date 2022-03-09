package Visuals.main;

import Visuals.Controller.DefaultVariables;
import Visuals.Controller.ReadFiles;
import Visuals.MazeGenerator.MazeForSale;
import Visuals.Path.Move;
import Visuals.Path.Position;
import Visuals.engine.graphics.Loader;
import Visuals.engine.graphics.MasterRenderer;
import Visuals.engine.graphics.models.RawModel;
import Visuals.engine.graphics.models.TexturedModel;
import Visuals.engine.graphics.textures.ModelTexture;
import Visuals.engine.graphics.textures.TerrainTexture;
import Visuals.engine.graphics.textures.TerrainTexturePack;
import Visuals.engine.graphics.textures.objConverter.ModelData;
import Visuals.engine.graphics.textures.objConverter.OBJFileLoader;
import Visuals.engine.io.Input;
import Visuals.engine.io.Window;
import Visuals.entities.Camera;
import Visuals.entities.Entity;
import Visuals.entities.Light;
import Visuals.entities.Player;
import org.lwjgl.glfw.GLFW;
import org.lwjglx.util.vector.Vector2f;
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;
import Visuals.terrain.Terrain;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.*;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public static final int WIDTH = 1600, HEIGHT = 900;
	public int i = 0;

	public Loader loader = new Loader();

	public RawModel guardModel;
	public RawModel intruderModel;
	public RawModel wallModel;
	public RawModel portalModel;

	public ModelData modelDataGuard;
	public ModelData modelDataIntruder;
	public ModelData modelDataWall;
	public ModelData modelDataPortal;

	public ModelTexture textureGuard;
	public ModelTexture textureIntruder;
	public ModelTexture textureWall;
	public ModelTexture texturePortal;

	public TexturedModel texturedModelGuard;
	public TexturedModel texturedModelIntruder;
	public TexturedModel texturedModelWall;
	public TexturedModel texturedModelPortal;


	public MasterRenderer renderer;

	public Player guard;
	public Player intruder;
	public Player camPlayer;

	public Camera camera;

	public List<Light> lights;
	public List<Entity> entities = new ArrayList<>();
	public List<Player> players = new ArrayList<>();
	public static Terrain terrain;

	public TerrainTexture backgroundTexture;
	public TerrainTexture rTexture;
	public TerrainTexture gTexture;
	public TerrainTexture bTexture;
	public TerrainTexturePack texturePack;
	public TerrainTexture blendMap;

	private static final float RED = 0.5f;
	private static final float GREEN = 0.5f;
	private static final float BLUE = 0.5f;

	private DefaultVariables variables;
	public static String testMapPath;
	public MazeForSale maze;
	public boolean generateMaze = true;
	public Position start;
	public Position end;
	public int[][] mazeMatrix;
	public int pathIndex = 0;
	public List<Position> path;
	public Input input;
	public long lastClick;



	public void start() {
		game = new Thread(this, "Simulation");
		game.start();
	}

	public void init() throws Exception {
		window = new Window(WIDTH, HEIGHT, "Simulation");
		window.setBackgroundColor(RED,GREEN,BLUE);
		window.create();
		renderer = new MasterRenderer(loader);
		testMapPath = "res/testmap.txt";


		//parser.readFile(testMapPath);
		variables = new DefaultVariables();
		variables.setVariables(ReadFiles.readFileAsString(testMapPath));


		// Loading in an object:
		// * step 1: get .obj file (from ../res/3D/)

		modelDataGuard = OBJFileLoader.loadOBJ("guard");
		modelDataIntruder = OBJFileLoader.loadOBJ("guard");
		modelDataWall = OBJFileLoader.loadOBJ("wall");
		modelDataPortal = OBJFileLoader.loadOBJ("portal");

		// * step 2: load the model data

		guardModel = loader.loadToVAO(modelDataGuard.getVertices(),modelDataGuard.getTextureCoords(),modelDataGuard.getNormals(),modelDataGuard.getIndices());
		intruderModel = loader.loadToVAO(modelDataIntruder.getVertices(),modelDataIntruder.getTextureCoords(),modelDataIntruder.getNormals(),modelDataIntruder.getIndices());
		wallModel = loader.loadToVAO(modelDataWall.getVertices(),modelDataWall.getTextureCoords(),modelDataWall.getNormals(),modelDataWall.getIndices());
		portalModel = loader.loadToVAO(modelDataPortal.getVertices(),modelDataPortal.getTextureCoords(),modelDataPortal.getNormals(),modelDataPortal.getIndices());

		// create texture for terrain
		backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
		rTexture = new TerrainTexture(loader.loadTexture("dirt"));
		gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
		bTexture = new TerrainTexture(loader.loadTexture("path"));
		texturePack = new TerrainTexturePack(backgroundTexture, rTexture,gTexture,bTexture);
		blendMap = new TerrainTexture(loader.loadTexture("blendMap"));

		// * step 3: Add textures and models together.

		texturedModelGuard = new TexturedModel(guardModel, new ModelTexture(loader.loadTexture("wallTexture")));
		texturedModelIntruder = new TexturedModel(intruderModel, new ModelTexture(loader.loadTexture("portalTexture")));
		texturedModelWall = new TexturedModel(wallModel, new ModelTexture(loader.loadTexture("wallTexture")));
		texturedModelPortal = new TexturedModel(portalModel, new ModelTexture(loader.loadTexture("portalTexture")));

		textureGuard = texturedModelGuard.getTexture();
		textureGuard.setShineDamper(5);
		textureGuard.setReflectivity((float)0.5);

		textureIntruder = texturedModelIntruder.getTexture();
		textureIntruder.setShineDamper(5);
		textureIntruder.setReflectivity((float)0.5);

		input = new Input();


		// Maze Generation
		if(generateMaze){

		int mazeHeight = 10;
		int mazeLength = 10;
		int line = 0;
		mazeMatrix = new int[mazeHeight*2+1][mazeLength*2+1];

		maze = new MazeForSale(mazeLength,mazeHeight);
		String a = maze.toString();
		System.out.println(a);

		// Create a stream to hold the output
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		System.out.println(maze.toString());
		System.out.flush();
		System.setOut(old);
		Scanner scanner = new Scanner(baos.toString());
		while (scanner.hasNextLine()) {
			String s = scanner.nextLine();
			for (int i = 0; i < s.length(); i++){
				char c = s.charAt(i);
				if(c == '1'){
					entities.add(new Entity(texturedModelIntruder, new Vector3f(line,0,i), 0, 270,  0, 1F,1));
					mazeMatrix[line][i] = 1;
				}
				else if(c == '0'){
					mazeMatrix[line][i] = 0;
				}
				else if(c == 'S'){
					start = new Position(line,i);
					mazeMatrix[line][i] = 0;
				}
				else if(c == 'E'){
					end = new Position(line,i);
					mazeMatrix[line][i] = 0;
				}
			}
			line++;
		}
		scanner.close();
		}


		path = Move.getPath(start, end, mazeMatrix);

		for (Position p : path){
			mazeMatrix[p.getX()][p.getY()] = 3;
			//System.out.println(p);
		}

		for (int j = 0; j < mazeMatrix.length; j++) {
			for (int k = 0; k < mazeMatrix[0].length; k++) {
				System.out.print(mazeMatrix[j][k]);
			}
			System.out.println("");
		}


		// generate terrain
		terrain = new Terrain(0,0,loader,texturePack, blendMap,"heightMap");

		// generate light
		lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(1000,1000,300), new Vector3f(1f,1f,1f)));


		// generate players
		// * step 4: Generate entities or players.

		ArrayList<ArrayList<Entity>> walls = createWallsFromFile();
		intruder = new Player(texturedModelGuard, new Vector3f(start.getX(),0,start.getY()),0,90,0,1,1);  //portal
		//guard = new Player(texturedModelGuard, new Vector3f(variables.getSpawnGuard().x,0,variables.getSpawnGuard().y),0,90,0,1,1);  //portal
		camPlayer = new Player(texturedModelIntruder, new Vector3f(10,0,10),0,90,0,1,1);

		for(ArrayList<Entity> wall : walls){
			entities.addAll(wall);
		}

		players.add(intruder);
		//players.add(guard);

		// put the camera
		camera = new Camera(intruder);

		lastClick = System.currentTimeMillis();
	}

	public void run() {
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
		while (!window.shouldClose() && !Input.isKeyDown(GLFW.GLFW_KEY_ESCAPE)) {
			update();
			render();
			if (Input.isKeyDown(GLFW.GLFW_KEY_F11)) window.setFullscreen(!window.isFullscreen());

			if (Input.isKeyDown(GLFW.GLFW_KEY_M) && pathIndex < path.size()) {

				long currTime = System.currentTimeMillis();
				if(currTime-lastClick > 100){
					Position pos = path.get(pathIndex);
					intruder.move(new Vector2f(pos.getX(),pos.getY()));
					pathIndex++;
					lastClick = currTime;
				}
			}

			if (Input.isKeyDown(GLFW.GLFW_KEY_N) && pathIndex < path.size() && pathIndex>0) {

				long currTime = System.currentTimeMillis();
				if(currTime-lastClick > 100){
					Position pos = path.get(pathIndex-1);
					intruder.move(new Vector2f(pos.getX(),pos.getY()));
					pathIndex--;
					lastClick = currTime;
				}
			}
		}

		renderer.cleanUp();
		loader.cleanUp();
		window.destroy();
	}



	private void update() {
		window.update();
	}

	private void render() {
		camera.move();
		i++;


		for(Player pieces : players){
			renderer.processEntity(pieces);
		}

		for(Entity entity : entities){
			renderer.processEntity(entity);
		}
		// * step 5: renderer.processEntity(nameOfEntity that you made at step 4.)

		renderer.processTerrain(terrain);

		renderer.render(lights,camera, new Vector4f(0,-1,0, 100));

		window.swapBuffers();

	}

	// Creation of walls

	private ArrayList<Entity> createWallFromParams(double x1, double y1, double x2, double y2){
		if(isParallelToX(y1, y2)){
			return createWallParallelToX(Math.abs(x2-x1), (int)Math.min(x1, x2), (int)Math.min(y1, y2));
		}
		return createWallParallelToY(Math.abs(y2-y1), (int)Math.min(x1, x2), (int)Math.min(y1, y2));
	}

	private boolean isParallelToX(double y1, double y2){
		return Math.abs(y2 - y1) == 1;
	}

	private ArrayList<Entity> createWallParallelToX(double wallLength, int startX, int startY){
		ArrayList<Entity> walls = new ArrayList<>();
		for(int i = 0; i < wallLength; i++)
			walls.add(new Entity(texturedModelWall, new Vector3f(startX + i,0,startY),0,0,0,1,1));
		return walls;
	}

	private ArrayList<Entity> createWallParallelToY(double wallLength, int startX, int startY){
		ArrayList<Entity> walls = new ArrayList<>();
		for(int i = 0; i < wallLength; i++)
			walls.add(new Entity(texturedModelWall, new Vector3f(startX,0,startY + i),0,90,0,1,1));
		return walls;
	}

	private ArrayList<ArrayList<Entity>> createWallsFromFile(){
		ArrayList<ArrayList<Entity>> walls = new ArrayList<>();

		// x,y,z,w: this order.

		walls.add(createWallFromParams(variables.getWall1().x, variables.getWall1().y, variables.getWall1().z, variables.getWall1().w));
		walls.add(createWallFromParams(variables.getWall2().x, variables.getWall2().y, variables.getWall2().z, variables.getWall2().w));
		walls.add(createWallFromParams(variables.getWall3().x, variables.getWall3().y, variables.getWall3().z, variables.getWall3().w));
		walls.add(createWallFromParams(variables.getWall4().x, variables.getWall4().y, variables.getWall4().z, variables.getWall4().w));
		walls.add(createWallFromParams(variables.getWall5().x, variables.getWall5().y, variables.getWall5().z, variables.getWall5().w));

		System.out.println(variables.getWall5().w);
		return walls;
	}


	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

}
