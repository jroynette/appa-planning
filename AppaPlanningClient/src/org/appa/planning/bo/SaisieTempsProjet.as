package org.appa.planning.bo
{
	[RemoteClass(alias="org.appa.planning.vo.SaisieTempsProjet")]
	[Bindable]
	public class SaisieTempsProjet
	{
		public var projet:Projet;
		
		//map<String(jour),SaisieTempsProjetJour
		public var temps:Object = new Object();

	}
}