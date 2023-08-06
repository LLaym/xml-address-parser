package ru.llaym;

import lombok.Getter;
import lombok.Setter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

@Getter
@Setter
@XmlAccessorType(XmlAccessType.FIELD)
public class Relation {
    @XmlAttribute(name = "OBJECTID")
    private String objectId;

    @XmlAttribute(name = "PARENTOBJID")
    private String parentObjectId;

    @XmlAttribute(name = "STARTDATE")
    private String startDate;

    @XmlAttribute(name = "ENDDATE")
    private String endDate;

    @XmlAttribute(name = "ISACTIVE")
    private String isActive;
}