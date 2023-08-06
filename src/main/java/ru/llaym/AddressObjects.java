package ru.llaym;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Getter
@Setter
@XmlRootElement(name = "ADDRESSOBJECTS")
@XmlAccessorType(XmlAccessType.FIELD)
public class AddressObjects {
    @XmlElement(name = "OBJECT")
    private List<Address> addresses;
}