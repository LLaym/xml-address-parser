package ru.llaym;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        String addressObjectsFilePath = "src/main/resources/AS_ADDR_OBJ.XML";
        String addressHierarchyFilePath = "src/main/resources/AS_ADM_HIERARCHY.XML";

        AddressService addressService = new AddressService(addressObjectsFilePath, addressHierarchyFilePath);

        String date = "2010-01-01";
        List<String> objectIds = List.of("1422396", "1450759", "1449192", "1451562");
        String addressType = "проезд";

        addressService.getAddressDescriptions(date, objectIds); // 1'st method
        addressService.getFullAddressesWithType(addressType); // 2'nd method
    }
}
