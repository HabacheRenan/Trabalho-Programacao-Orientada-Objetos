package controller;

public class Controller {
	private static PokedexController pokedexController = new PokedexController();
	private static HabilidadesController habilidadesController = new HabilidadesController();
	public static PokedexController getPokedexController() {
		return pokedexController;
	}
	public void setPokedexController(PokedexController pokedexController) {
		this.pokedexController = pokedexController;
	}
	public static HabilidadesController getHabilidadesController() {
		return  habilidadesController;
	}
	public void setHabilidadesController(HabilidadesController habilidadesController) {
		this.habilidadesController = habilidadesController;
	}
	
	
}
