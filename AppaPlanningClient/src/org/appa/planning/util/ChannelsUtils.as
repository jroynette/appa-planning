package org.appa.planning.util
{
	import mx.messaging.ChannelSet;
	import mx.messaging.channels.AMFChannel;

	[Bindable]
	public class ChannelsUtils
	{
		public var channelSet:ChannelSet;
		
		public static const instance:ChannelsUtils = new ChannelsUtils();
		
		public function ChannelsUtils(){
			
			channelSet = new ChannelSet();
			//decommenter
			//channelSet.addChannel(new AMFChannel("amf","http://localhost:8080/appaPlanningAdmin/messagebroker/amf"));
			//channelSet.addChannel(new AMFChannel("amf","http://appaplanningadmin.cloudfoundry.com/messagebroker/amf"));
			channelSet.addChannel(new AMFChannel("amf","http://91.121.208.158:8180/appaPlanningAdmin/messagebroker/amf"));
		}
	}
}
