package org.appa.planning.bo
{
	import mx.collections.ArrayCollection;

	[RemoteClass(alias="org.appa.planning.vo.SaisieTempsSemaine")]
	[Bindable]
	public class SaisieTempsSemaine
	{
		public var date:Date;
		
		//list<saisieTempsProjet>
		public var projets:ArrayCollection = new ArrayCollection();
		
		//map<String,boolean>
		public var dejeunersExterne:Object = new Object();
		
		//map<String,boolean>
		public var joursFeries:Object = new Object();
		
		public function total(jour:String):int{
			var result:int = 0;
			for each (var projet:SaisieTempsProjet in projets) 
			{
				if(projet.temps[jour].heures != null){
				 	result += projet.temps[jour].heures;
				}
			}
			return result;
		}
	}
}