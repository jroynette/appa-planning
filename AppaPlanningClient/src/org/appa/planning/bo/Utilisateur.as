package org.appa.planning.bo
{
	[RemoteClass(alias="org.appa.planning.bo.Utilisateur")]
	[Bindable]
	public class Utilisateur
	{
		public var id:Number;
				
		public var login:String;
					
		public var nom:String;
				
		public var prenom:String;
		
		public var role:String;
		
		public var email:String;
		
		public function toString():String{
			return prenom + " " + nom;
		}
				
	}
}