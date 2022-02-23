package Visuals.main;

import Visuals.Controller.DefaultVariables;
import Visuals.Controller.ReadFiles;
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
import org.lwjglx.util.vector.Vector3f;
import org.lwjglx.util.vector.Vector4f;
import Visuals.terrain.Terrain;

import java.util.ArrayList;
import java.util.List;

public class Main implements Runnable {

	public Thread game;
	public Window window;
	public static final int WIDTH = 1600, HEIGHT = 900;
	public int i = 0;

	public Loader loader = new Loader();

	public RawModel guardModel;
	public RawModel intruderModel;

	public ModelData modelDataGuard;
	public ModelData modelDataIntruder;


	public ModelTexture textureGuard;
	public ModelTexture textureIntruder;


	public TexturedModel texturedModelGuard;
	public TexturedModel texturedModelIntruder;


	public MasterRenderer renderer;

	public Player guard;
	public Player intruder;


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

		modelDataGuard = OBJFileLoader.loadOBJ("wall");
		modelDataIntruder = OBJFileLoader.loadOBJ("portal");

		// * step 2: load the model data

		guardModel = loader.loadToVAO(modelDataGuard.getVertices(),modelDataGuard.getTextureCoords(),modelDataGuard.getNormals(),modelDataGuard.getIndices());
		intruderModel = loader.loadToVAO(modelDataIntruder.getVertices(),modelDataIntruder.getTextureCoords(),modelDataIntruder.getNormals(),modelDataIntruder.getIndices());

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

		textureGuard = texturedModelGuard.getTexture();
		textureGuard.setShineDamper(5);
		textureGuard.setReflectivity((float)0.5);

		textureIntruder = texturedModelIntruder.getTexture();
		textureIntruder.setShineDamper(5);
		textureIntruder.setReflectivity((float)0.5);


		// generate terrain
		terrain = new Terrain(0,0,loader,texturePack, blendMap,"heightMap");

		// generate light
		lights = new ArrayList<>();
		lights.add(new Light(new Vector3f(1000,1000,300), new Vector3f(1f,1f,1f)));

		// create GUI elements

		// generate players
		// * step 4: Generate entities or players.

		ArrayList<ArrayList<Entity>> walls = createWallsFromFile();
		intruder = new Player(texturedModelIntruder, new Vector3f(variables.getSpawnAreaIntruders().z,0,variables.getSpawnAreaIntruders().w),0,90,0,1,1);  //portal
		guard = new Player(texturedModelGuard, new Vector3f(variables.getSpawnAreaGuards().z,0,variables.getSpawnAreaGuards().w),0,90,0,1,1);  //portal


		for(ArrayList<Entity> wall : walls){
			entities.addAll(wall);
		}

		players.add(intruder);

		// put the camera
		camera = new Camera(intruder);
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


			if (Input.isKeyDown(GLFW.GLFW_KEY_M) /*&& !isOpenMoveBox*/ ) {
				// Action after 'M' key press.
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
			walls.add(new Entity(texturedModelGuard, new Vector3f(startX + i,0,startY),0,0,0,1,1));
		return walls;
	}

	private ArrayList<Entity> createWallParallelToY(double wallLength, int startX, int startY){
		ArrayList<Entity> walls = new ArrayList<>();
		for(int i = 0; i < wallLength; i++)
			walls.add(new Entity(texturedModelGuard, new Vector3f(startX,0,startY + i),0,90,0,1,1));
		return walls;
	}

	private ArrayList<ArrayList<Entity>> createWallsFromFile(){
		ArrayList<ArrayList<Entity>> walls = new ArrayList<>();

		// x,w,z,y this order.

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
