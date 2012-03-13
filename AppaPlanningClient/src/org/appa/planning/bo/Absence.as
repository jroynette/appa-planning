package org.appa.planning.bo
{
	import mx.controls.DateField;

	[RemoteClass(alias="org.appa.planning.bo.Absence")]
	[Bindable]
	public class Absence
	{	
		public var id:int;
		
		public var utilisateur:Utilisateur;
		
		public var dateDebut:Date;
		
		public var debutPM:Boolean;
		
		public var dateFin:Date;
		
		public var finAM:Boolean;
		
		public var statut:String;
		
		public var type:String;
		
		public var commentaire:String;
		
	}
}