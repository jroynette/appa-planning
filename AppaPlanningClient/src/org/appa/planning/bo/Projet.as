package org.appa.planning.bo
{
	[RemoteClass(alias="org.appa.planning.bo.Projet")]
	[Bindable]
	public class Projet
	{
		public var id:int;
				
		public var nom:String;
		
		public var description:String;
		
		public var annee:int;
	}
}