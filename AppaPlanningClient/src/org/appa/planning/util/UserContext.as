package org.appa.planning.util
{
	import mx.collections.ArrayCollection;
	
	import org.appa.planning.bo.Utilisateur;

	[Bindable]
	public class UserContext
	{
		public var user:Utilisateur;
		
		public var compteurs:Object;
		
		public var absences:ArrayCollection;
		
		public static const instance:UserContext = new UserContext(); 
	}
}