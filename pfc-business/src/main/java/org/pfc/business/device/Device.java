package org.pfc.business.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;

import com.vividsolutions.jts.geom.Point;

/**
 * 
 * @author Sergio García Ramos <sergio.garcia@udc.es>
 *
 */
@Entity
@Table(name = "Device")
public class Device {

        private Long deviceId;
        private String deviceName;
        private String description;
        private String ipAddress;
        private String publicCommunity;
        private String snmpPort;
        private Point position;
        private double lat;
        private double lng;
        private long version;

        public Device() {}

        public Device(String deviceName, String description, String ipAddress, String publicCommunity, String snmpPort, Point position, double lat, double lng) {
                this.deviceName = deviceName;
                this.description = description;
                this.ipAddress = ipAddress;
                this.publicCommunity = publicCommunity;
                this.snmpPort = snmpPort;
                this.position = position;
                this.lat = lat;
                this.lng = lng;
        }


		@Id
		@GeneratedValue
		@Column(name = "devId")
		public Long getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(Long deviceId) {
			this.deviceId = deviceId;
		}

		@Column(name = "devName")
		public String getDeviceName() {
			return deviceName;
		}

		public void setDeviceName(String deviceName) {
			this.deviceName = deviceName;
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

		public void setVersion(long version) {
			this.version = version;
		}

		@Version
		public long getVersion() {
			return version;
		}
    
}

