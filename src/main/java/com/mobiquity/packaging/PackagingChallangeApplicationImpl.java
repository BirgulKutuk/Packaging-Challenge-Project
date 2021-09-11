package com.mobiquity.packaging;

import com.mobiquity.exception.APIException;
import com.mobiquity.util.Item;
import com.mobiquity.util.Package;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PackagingChallangeApplicationImpl implements PackagingChallangeApplication {

    private static final Logger LOGGER = Logger.getLogger(PackagingChallangeApplicationImpl.class.getName());
    private String SPLIT_REGEX = "[:\\(\\)\\s]+";
    private static final Double MAX_PACKAGE_WEIGHT = 100.0;
    private static final int MAX_ITEM_COUNT = 15;
    private static final Double MAX_ITEM_COST = 100.0;


    public String processPackageFile(String filePath) throws APIException, IOException {

        String processResult = "";
        List<Package> packageList = new ArrayList<>();

        try {
            // read the file
            List<String> inputFileRows = readFile( filePath );

            // split input and fill dto for each row
            for (String row : inputFileRows) {
                packageList.add( splitInput(row) );
            }

            // determine which item to choose
            for (Package p : packageList) {
                processResult = processResult.concat( determineWhichItemToChoose( p ) ).concat("\n");
            }

        } catch (APIException apiException) {
            LOGGER.log(Level.SEVERE, apiException.getMessage());
            throw apiException;
        } catch (IOException ioException) {
            LOGGER.log(Level.SEVERE, ioException.getMessage());
            throw ioException;
        }

        return processResult;
    }

    public List<String> readFile( String filePath ) throws IOException {

        Path path = Paths.get( filePath );
        List<String> inputFileRows = Files.readAllLines( path, StandardCharsets.UTF_8 );

        return inputFileRows;
    }

    public Package splitInput( String row ) throws APIException {

        Package aPackage = new Package();
        List<Item> itemList = new ArrayList<Item>();
        Item anItem;

        String[] arrOfStr = row.split( SPLIT_REGEX );
        aPackage.setPackageLimit( Double.valueOf(arrOfStr[0]) );

        // validate to package weight is under max limit
        if ( aPackage.getPackageLimit() > MAX_PACKAGE_WEIGHT) {
            return null;
        }

        for (int i = 1; i < arrOfStr.length; i++) {
            String[] arrOfItem = arrOfStr[i].split(",");

            // check item's index number is serial and max item cost
            if ( i == Integer.valueOf(arrOfItem[0]) && Double.valueOf(arrOfItem[2].substring(1)) <= MAX_ITEM_COST) {
                anItem = new Item(Integer.valueOf(arrOfItem[0]), Double.valueOf(arrOfItem[1]), Double.valueOf(arrOfItem[2].substring(1)));
                itemList.add( anItem );
            } else {
                // if item's index number isn't increase one by one, it is an invaild data
                return null;
            }
        }
        aPackage.setPackageItems( itemList );

        return aPackage;
    }

    public String determineWhichItemToChoose (Package aPackage) {

        String selectedItems = "";

        if (aPackage == null) {
            return "-";
        }

        // sort items in descending order of cost
        aPackage.getPackageItems().sort(Comparator.comparing(Item::getCost).reversed());

        Double packageLimit = aPackage.getPackageLimit();

        for (int i = 0; i < aPackage.getPackageItems().size(); i++) {

            // check item count
            if (!isThereAnySpaceForOneMoreItem( selectedItems )) {
                break;
            }

            Double cost = aPackage.getPackageItems().get(i).getCost();
            Double weight = aPackage.getPackageItems().get(i).getWeight();
            int indexNumber = aPackage.getPackageItems().get(i).getIndexNumber();

            if ( weight <= packageLimit ) {
                Item item = searchLowerWeigthWithSameCost(aPackage.getPackageItems(), weight, cost);
                if ( item == null ) {
                    if (!isTheCurrentItemInSelectedItemsList(selectedItems, indexNumber)) {
                        selectedItems = selectedItems.concat(String.valueOf(indexNumber)).concat(",");
                        packageLimit = packageLimit - weight;
                    }
                } else {
                    if (!isTheCurrentItemInSelectedItemsList(selectedItems, item.getIndexNumber())) {
                        selectedItems = selectedItems.concat(String.valueOf( item.getIndexNumber() )).concat(",");
                        packageLimit = packageLimit - item.getWeight();
                    } else {
                        selectedItems = selectedItems.concat(String.valueOf( indexNumber )).concat(",");
                        packageLimit = packageLimit - weight;
                    }
                }
            }
        }

        if ( selectedItems.length() == 0 ) {
            selectedItems = "-";
        }
        else if ( selectedItems.endsWith(",") ) {
            selectedItems = selectedItems.substring(0, selectedItems.length() - 1);
        }

        return selectedItems;
    }

    public Item searchLowerWeigthWithSameCost(List<Item> items, Double weight, Double cost) {

        List<Item> item = items.stream().filter(i -> i.getWeight() < weight && i.getCost() == cost).collect(Collectors.toList());
        if(!item.isEmpty()){
            item.sort(Comparator.comparing(Item::getWeight));
            return item.get(0);
        } else {
            return null;
        }
    }

    public boolean isThereAnySpaceForOneMoreItem (String selectedItems) {

        String[] alreadySelectedItems = selectedItems.split(",");

        if ( alreadySelectedItems.length >= MAX_ITEM_COUNT) {
            return false;
        }
        return true;
    }

    public boolean isTheCurrentItemInSelectedItemsList (String selectedItems, int currentItemIndex) {

        if (selectedItems == "") {
            return false;
        }

        String[] alreadySelectedItems = selectedItems.split(",");

        for (String item : alreadySelectedItems) {
            if (currentItemIndex == Integer.valueOf(item)) {
                return true;
            }
        }
        return false;
    }
}
