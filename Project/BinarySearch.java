/** Searching functionality searches the file of data for specified characteristics. When user want data of any given
 * climate data it will be shown
 * @author  Christine, Ewurama, Daniel ,Dave 
 */
public class BinarySearch {
    ClimateRecord[][] cache = new ClimateRecord[5][];
    /**
     * searchEntry is a method to find all matching records from the climate record data that will be loaded into the application.
     * This is a dynamic search such that only the provided parameters will be used to execute the search. Parameters that are not provided
     * will be represented as null
     * @param list this takes in the list for which the search has to be made
     * @param temperature this takes the temperature value for which the search has to be made
     * @param precipitation this takes the precipitation value for which the search has to be made
     * @param month this takes the month value for which the search has to be made
     * @param year this takes the year value for which the search has to be made
     * @param region this takes the region value for which the search has to be made
     * @param duplicate this takes a value to indicate if the user wants duplicates of their search matches or they just want a single entry found
     */
  

    public LinkedList searchEntry( ClimateRecord[] list,Double temperature,Double precipitation, Integer month, Integer year, String region, boolean duplicate){
        int low=0;
        int high=list.length-1;

        LinkedList finalList = new LinkedList(); //linked list to dynamically keep record of the matched entries so no need for resizing
        //since we are not using an array which requires a static size

        if(region!=null){ //checking if the region entry was provided as a search criterion
            if(cache[0]==null){ //we check our sorted cache to see if we ever did a region sort, so we don't do another unnecessarily
                DataSorting.mergeSort(list,"region"); //we do a new sort since the cache entry for this type of sort is not available
                cache[0] = list; //we then update our cache
            }
            list=cache[0]; //we update our list with cache data in any other case so we have our sorted data to continue with the search next

            whileLoop: //added a label to the outer loop because we'll need to break out from it later while in an inner loop
            while(low<=high){ //condition for binary search to ensure traversal of whole list
                int mid = (low+high)/2;
                if(list[mid].getRegion().equalsIgnoreCase(region)){//if we find a match for the region of interest

                    //The below block of code checks for the availability of the possible search criteria, if available, there is a check to see if there is a match
                    //This makes it possible for a user to either search according to a single or multiple criteria
                    boolean yearMatch = (year == null || list[mid].getYear() == year);
                    boolean temperatureMatch = (temperature == null || list[mid].getTemperature() == temperature);
                    boolean monthMatch = (month == null || list[mid].getMonth() == month);
                    boolean precipitationMatch = (precipitation == null || list[mid].getPrecipitation() == precipitation);


                    for(int i=mid;i< list.length; i++){//we search to the right to find other entries that meet the user's search criteria
                        if(!list[i].getRegion().equalsIgnoreCase(region)) break; //the for loop is supposed to break at any point which does not meet the primary search criterion
                        //since the data is sorted according to the primary search criterion, once a particular entry does not meet the criterion, no other entry beyond that will meet that
                        if(yearMatch && temperatureMatch && monthMatch && precipitationMatch){ //checking to see if the rest of the criteria meet the current entry
                            finalList.addData(list[i]); //if all criteria met, we add to the final list
                            if(!duplicate){//if user requested for a single search match, we break at this point since we have found an entry
                                break whileLoop;
                            }
                        }
                    }
                    for(int i=mid-1;i>= 0; i--){//we also search to the left to find other entries that meet the user's search criteria
                        //the rest of the code performs the same way as the earlier for loop which searches to the right
                        if(!list[i].getRegion().equalsIgnoreCase(region)) break;
                        if(yearMatch && temperatureMatch && monthMatch && precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }
                    }
                    break;

                }
                else if(list[mid].getRegion().compareToIgnoreCase(region)<0){//making comparison based on strings in order to either move a search to the right
                    //side or to the left side
                    low=mid+1;
                }
                else if(list[mid].getRegion().compareToIgnoreCase(region)>0){
                    high = mid-1;
                }
            }
        }
        //The rest of the if else block use the same structure and method as above, but use different parameters as the primary search criterion before
        //checking for the rest of the parameters for a match
        else if(month!=null){//checking if month search criterion has been provided
            if(cache[1]==null){
                DataSorting.mergeSort(list,"month");
                cache[1] = list;
            }
            list=cache[1];

            whileLoop:
            while(low<=high){
                int mid = (low+high)/2;
                if(list[mid].getMonth()==month){
                    boolean yearMatch = (year == null || list[mid].getYear() == year);
                    boolean temperatureMatch = (temperature == null || list[mid].getTemperature() == temperature);
                    boolean precipitationMatch = (precipitation == null || list[mid].getPrecipitation() == precipitation);


                    for(int i=mid;i< list.length; i++){
                        if(list[i].getMonth()!=month) break;
                        if(yearMatch && temperatureMatch && precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }
                    }
                    for(int i=mid-1;i>= 0; i--){
                        if(list[i].getMonth()!=month) break;
                        if(yearMatch && temperatureMatch && precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }
                    }
                    break;

                }
                else if(list[mid].getMonth()<month){
                    low=mid+1;
                }
                else if(list[mid].getMonth()>month){
                    high = mid-1;
                }
            }
        }
        else if(year!=null){
            if(cache[2]==null){
                DataSorting.mergeSort(list,"year");
                cache[2] = list;
            }
            list=cache[2];

            whileLoop:
            while(low<=high){
                int mid = (low+high)/2;
                if(list[mid].getYear()==year){
                    boolean temperatureMatch = (temperature == null || list[mid].getTemperature() == temperature);
                    boolean precipitationMatch = (precipitation == null || list[mid].getPrecipitation() == precipitation);



                    for(int i=mid;i< list.length; i++){
                        if(list[i].getYear()!=year) break;
                        if(temperatureMatch && precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }
                    }
                    for(int i=mid-1;i>= 0; i--){
                        if(list[i].getYear()!=year) break;
                        if(temperatureMatch && precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }
                    }

                    break;
                }
                else if(list[mid].getYear()<year){
                    low=mid+1;
                }
                else if(list[mid].getYear()>year){
                    high = mid-1;
                }
            }
        }
        else if(temperature!=null){
            if(cache[3]==null){
                DataSorting.mergeSort(list,"temperature");
                cache[3] = list;
            }
            list=cache[3];

            whileLoop:
            while(low<=high){
                int mid = (low+high)/2;
                if(list[mid].getTemperature()==temperature){
                    boolean precipitationMatch = (precipitation == null || list[mid].getPrecipitation() == precipitation);

                    for(int i=mid;i< list.length; i++){
                        if(list[i].getTemperature()!=temperature) break;
                        if(precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }

                    }
                    for(int i=mid-1;i>= 0; i--){
                        if(list[i].getTemperature()!=temperature) break;
                        if(precipitationMatch){
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }
                        }

                    }
                    break;
                }
                else if(list[mid].getTemperature()<temperature){
                    low=mid+1;
                }
                else if(list[mid].getTemperature()>temperature){
                    high = mid-1;
                }
            }

        }
        else if(precipitation!=null){
            if(cache[4]==null){
                DataSorting.mergeSort(list,"precipitation");
                cache[4] = list;
            }
            list=cache[4];

            whileLoop:
            while(low<=high){
                int mid = (low+high)/2;
                if(list[mid].getPrecipitation()==precipitation){
                    for(int i=mid;i< list.length; i++){
                        if(list[i].getPrecipitation()!=precipitation) break;
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }

                    }
                    for(int i=mid-1;i>= 0; i--){
                        if(list[i].getPrecipitation()!=precipitation) break;
                            finalList.addData(list[i]);
                            if(!duplicate){
                                break whileLoop;
                            }

                    }
                    break;
                }
                else if(list[mid].getPrecipitation()<precipitation){
                    low=mid+1;
                }
                else if(list[mid].getPrecipitation()>precipitation){
                    high = mid-1;
                }
            }

        }
        else{
            System.out.println("No parameter has been provided for search");
        }
        if(finalList.size==0) {
            System.out.println("No records have been found that match your search criteria");
            return  null;
        }
        else{
            return finalList;
        }

    }
}
