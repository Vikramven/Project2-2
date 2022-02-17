package Visuals.main;

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
	public List<Entity> entities;
	public List<Player> players;
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


	public void start() {
		game = new Thread(this, "Simulation");
		game.start();
	}

	public void init() throws Exception {
		window = new Window(WIDTH, HEIGHT, "Simulation");
		window.setBackgroundColor(RED,GREEN,BLUE);
		window.create();
		renderer = new MasterRenderer(loader);

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

		guard = new Player(texturedModelGuard, new Vector3f(25,terrain.getHeightOfTerrain(25,70),70),0,90,0,1,1);
		intruder = new Player(texturedModelIntruder, new Vector3f(25,terrain.getHeightOfTerrain(25,70),71),0,90,0,1,1);


		// put the camera
		camera = new Camera(guard);

		players = new ArrayList<>();

		players.add(guard);
		players.add(intruder);

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

		// * step 5: renderer.processEntity(nameOfEntity that you made at step 4.)

		renderer.processTerrain(terrain);

		renderer.render(lights,camera, new Vector4f(0,-1,0, 100));

		window.swapBuffers();

	}

	public static int getWIDTH() {
		return WIDTH;
	}

	public static int getHEIGHT() {
		return HEIGHT;
	}

}
