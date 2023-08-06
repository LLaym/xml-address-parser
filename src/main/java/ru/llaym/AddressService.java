package ru.llaym;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AddressService {
    private AddressObjects addressObjects;
    private RelationObjects relationObjects;

    public AddressService(String addressObjectsFilePath, String addressHierarchyFilePath) {
        try {
            JAXBContext context = JAXBContext.newInstance(AddressObjects.class, RelationObjects.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            File addressObjectFile = new File(addressObjectsFilePath);
            File addressHierarchyFile = new File(addressHierarchyFilePath);

            addressObjects = (AddressObjects) unmarshaller.unmarshal(addressObjectFile);
            relationObjects = (RelationObjects) unmarshaller.unmarshal(addressHierarchyFile);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public void getAddressDescriptions(String date, List<String> objectIds) {
        List<String> descriptions = new ArrayList<>();

        for (String objectId : objectIds) {
            List<Address> addresses = getAddressesById(objectId);
            for (Address address : addresses) {
                if (address != null && isAddressValid(address, date)) {
                    String description = objectId + ": " + address.getTypeName() + " " + address.getName();
                    descriptions.add(description);
                }
            }
        }

        for (String description : descriptions) {
            System.out.println(description);
        }
    }

    private List<Address> getAddressesById(String objectId) {
        List<Address> addresses = new ArrayList<>();
        for (Address address : addressObjects.getAddresses()) {
            if (address.getObjectId().equals(objectId)) {
                addresses.add(address);
            }
        }
        return addresses;
    }

    private boolean isAddressValid(Address address, String date) {
        return address.getStartDate().compareTo(date) <= 0 && address.getEndDate().compareTo(date) >= 0;
    }

    public void getFullAddressesWithType(String addressType) {
        List<String> result = addressObjects.getAddresses().stream()
                .filter(address -> addressType.equals(address.getTypeName()))
                .filter(address -> address.getIsActual().equals("1") && address.getIsActive().equals("1"))
                .map(this::getFullAddress)
                .collect(Collectors.toList());

        for (String fullAddress : result) {
            System.out.println(fullAddress);
        }
    }

    private String getFullAddress(Address address) {
        StringBuilder sb = new StringBuilder();
        String objectId = address.getObjectId();

        while (objectId != null) {
            Address obj = findAddressById(objectId);
            if (obj != null) {
                if (sb.length() > 0) {
                    sb.insert(0, ", ");
                }
                sb.insert(0, obj.getName());
            }
            objectId = findParentIdById(objectId);
        }

        return sb.toString();
    }

    private Address findAddressById(String id) {
        return addressObjects.getAddresses().stream()
                .filter(a -> a.getObjectId().equals(id)).findFirst()
                .orElse(null);
    }

    private String findParentIdById(String id) {
        Relation relation = relationObjects.getRelations().stream()
                .filter(i -> i.getObjectId().equals(id)).findFirst()
                .orElse(null);

        return relation != null ? relation.getParentObjectId() : null;
    }
}