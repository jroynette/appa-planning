package org.appa.planning.util
{
	import org.appa.planning.bo.Utilisateur;

	[Bindable]
	public class UserContext
	{
		public var user:Utilisateur;
		
		public static const instance:UserContext = new UserContext(); 
	}
}