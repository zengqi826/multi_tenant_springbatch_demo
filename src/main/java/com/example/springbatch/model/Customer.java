/**
 * 
 */
package com.example.springbatch.model;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author 86211
 *
 */
@Entity
@Table(name="customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customerid")
    private long id;
    @Column(name="customerno")
    private long customerNo;
    @Column(name="customername")
    private String customerName;
    @Column(name="gender")
    private String gender;
    @Column(name="birthday")
    private Date birthday;
    @Column(name="address")
    private String address;
    @Column(name="phoneno")
    private String phoneNo;
    @Column(name="customertype")
    private String customerType;
    @Column(name="emailaddress")
    private String emailAddress;
    @Column(name="flag")
    private String flag;
    @Column(name="version")
    private String version;
    @Column(name="updatetimestamp")
    private LocalDateTime updateTimeStamp;
    
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getCustomerNo() {
        return customerNo;
    }
    public void setCustomerNo(long customerNo) {
        this.customerNo = customerNo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPhoneNo() {
        return phoneNo;
    }
    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public String getFlag() {
        return flag;
    }
    public void setFlag(String flag) {
        this.flag = flag;
    }
    
    public String getVersion() {
        return version;
    }
    public void setVersion(String version) {
        this.version = version;
    }
    public LocalDateTime getUpdateTimeStamp() {
        return updateTimeStamp;
    }
    public void setUpdateTimeStamp(LocalDateTime updateTimeStamp) {
        this.updateTimeStamp = updateTimeStamp;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((address == null) ? 0 : address.hashCode());
        result = prime * result + ((birthday == null) ? 0 : birthday.hashCode());
        result = prime * result + (int) (id ^ (id >>> 32));
        result = prime * result + ((customerName == null) ? 0 : customerName.hashCode());
        result = prime * result + (int) (customerNo ^ (customerNo >>> 32));
        result = prime * result + ((customerType == null) ? 0 : customerType.hashCode());
        result = prime * result + ((emailAddress == null) ? 0 : emailAddress.hashCode());
        result = prime * result + ((flag == null) ? 0 : flag.hashCode());
        result = prime * result + ((gender == null) ? 0 : gender.hashCode());
        result = prime * result + ((phoneNo == null) ? 0 : phoneNo.hashCode());
        result = prime * result + ((version == null) ? 0 : version.hashCode());
        result = prime * result + ((updateTimeStamp == null) ? 0 : updateTimeStamp.hashCode());
        return result;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Customer other = (Customer) obj;
        if (address == null) {
            if (other.address != null) {
                return false;
            }
        } else if (!address.equals(other.address)) {
            return false;
        }
        if (birthday == null) {
            if (other.birthday != null) {
                return false;
            }
        } else if (!birthday.equals(other.birthday)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (customerName == null) {
            if (other.customerName != null) {
                return false;
            }
        } else if (!customerName.equals(other.customerName)) {
            return false;
        }
        if (customerNo != other.customerNo) {
            return false;
        }
        if (customerType == null) {
            if (other.customerType != null) {
                return false;
            }
        } else if (!customerType.equals(other.customerType)) {
            return false;
        }
        if (emailAddress == null) {
            if (other.emailAddress != null) {
                return false;
            }
        } else if (!emailAddress.equals(other.emailAddress)) {
            return false;
        }
        if (flag == null) {
            if (other.flag != null) {
                return false;
            }
        } else if (!flag.equals(other.flag)) {
            return false;
        }
        if (gender == null) {
            if (other.gender != null) {
                return false;
            }
        } else if (!gender.equals(other.gender)) {
            return false;
        }
        if (phoneNo == null) {
            if (other.phoneNo != null) {
                return false;
            }
        } else if (!phoneNo.equals(other.phoneNo)) {
            return false;
        }
        if (version == null) {
            if (other.version != null) {
                return false;
            }
        } else if (!version.equals(other.version)) {
            return false;
        }
        if (updateTimeStamp == null) {
            if (other.updateTimeStamp != null) {
                return false;
            }
        } else if (!updateTimeStamp.equals(other.updateTimeStamp)) {
            return false;
        }
        return true;
    }
    @Override
    public String toString() {
        return "Customer {customerId=" + id + ", customerNo=" + customerNo + ", customerName=" + customerName
                + ", gender=" + gender + ", birhday=" + birthday + ", address=" + address + ", phoneNo=" + phoneNo
                + ", customerType=" + customerType + ", emailAddress=" + emailAddress + ", flag=" + flag
                +". version=" + version + ", updateTimeStamp=" + updateTimeStamp + "}";
    }
    
}
