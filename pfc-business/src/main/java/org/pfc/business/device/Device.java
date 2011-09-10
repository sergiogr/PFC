package org.pfc.business.device;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Version;

import org.pfc.business.product.Product;

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
        private Product product;
        private long version;
        
        public Device() {}

        public Device(String deviceName, String description, String ipAddress, String publicCommunity, String snmpPort, Point position) {
                this.deviceName = deviceName;
                this.description = description;
                this.ipAddress = ipAddress;
                this.publicCommunity = publicCommunity;
                this.snmpPort = snmpPort;
                this.position = position;
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

		@Version
		public long getVersion() {
			return version;
		}
		
		public void setVersion(long version) {
			this.version = version;
		}

		@ManyToOne(optional=true, fetch=FetchType.LAZY)
		@JoinColumn(name="prodId")
		public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result
					+ ((description == null) ? 0 : description.hashCode());
			result = prime * result
					+ ((deviceId == null) ? 0 : deviceId.hashCode());
			result = prime * result
					+ ((deviceName == null) ? 0 : deviceName.hashCode());
			result = prime * result
					+ ((ipAddress == null) ? 0 : ipAddress.hashCode());
			result = prime * result
					+ ((position == null) ? 0 : position.hashCode());
			result = prime
					* result
					+ ((publicCommunity == null) ? 0 : publicCommunity
							.hashCode());
			result = prime * result
					+ ((snmpPort == null) ? 0 : snmpPort.hashCode());
			result = prime * result + (int) (version ^ (version >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (!(obj instanceof Device)) {
				return false;
			}
			Device other = (Device) obj;
			if (description == null) {
				if (other.description != null)
					return false;
			} else if (!description.equals(other.description))
				return false;
			if (deviceId == null) {
				if (other.deviceId != null)
					return false;
			} else if (!deviceId.equals(other.deviceId))
				return false;
			if (deviceName == null) {
				if (other.deviceName != null)
					return false;
			} else if (!deviceName.equals(other.deviceName))
				return false;
			if (ipAddress == null) {
				if (other.ipAddress != null)
					return false;
			} else if (!ipAddress.equals(other.ipAddress))
				return false;
			if (position == null) {
				if (other.position != null)
					return false;
			} else if (!position.equals(other.position))
				return false;
			if (publicCommunity == null) {
				if (other.publicCommunity != null)
					return false;
			} else if (!publicCommunity.equals(other.publicCommunity))
				return false;
			if (snmpPort == null) {
				if (other.snmpPort != null)
					return false;
			} else if (!snmpPort.equals(other.snmpPort))
				return false;
			if (version != other.version)
				return false;
			return true;
		}
		

}

