package org.appa.planning.ihm.saisietemps{
import mx.controls.Label;
import mx.controls.listClasses.*;

public class TotalLabel extends Label {

	private const POSITIVE_COLOR:uint = 0x000000; // Black
	private const NEGATIVE_COLOR:uint = 0xFF0000; // Red
	
	override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void {
		
		super.updateDisplayList(unscaledWidth, unscaledHeight);
	
		setStyle("color", (parseFloat(text) > 8) ? NEGATIVE_COLOR : POSITIVE_COLOR);
	}
}
}