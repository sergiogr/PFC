package org.pfc.business.device;

import com.vividsolutions.jts.geom.Point;


public class Device {


        private Long id;

        private String name;

        private String description;

        private String ipAddress;
        
        private String publicCommunity;
        
        private String snmpPort;

        private Point position;
        
        private double lat;
        
        private double lng;

        public Device() {
        	
        }

        public Device(String name, String description, String ipAddress, String publicCommunity, String snmpPort, Point position, double lat, double lng) {
                this.name = name;
                this.description = description;
                this.ipAddress = ipAddress;
                this.publicCommunity = publicCommunity;
                this.snmpPort = snmpPort;
                this.position = position;
                this.lat = lat;
                this.lng = lng;
        }

		public Long getId() {
			return id;
		}

		public void setId(Long id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getIpAddress() {
			return ipAddress;
		}

		public void setIpAddress(String ipAddress) {
			this.ipAddress = ipAddress;
		}

		public String getPublicCommunity() {
			return publicCommunity;
		}

		public void setPublicCommunity(String publicCommunity) {
			this.publicCommunity = publicCommunity;
		}

		public String getSnmpPort() {
			return snmpPort;
		}

		public void setSnmpPort(String snmpPort) {
			this.snmpPort = snmpPort;
		}

		public void setPosition(Point position) {
			this.position = position;
		}

		public Point getPosition() {
			return position;
		}

		public double getLat() {
			return lat;
		}

		public void setLat(double lat) {
			this.lat = lat;
		}

		public double getLng() {
			return lng;
		}

		public void setLng(double lng) {
			this.lng = lng;
		}
    
}

