import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class AirPlaneSeats {

    public static void main(String[] args) {

        Scanner scan = new Scanner(System.in);

        System.out.println("Input for number of arrays");

        int n = scan.nextInt();

        int totalSeats = 0;

        int maxRow = 0;

        List<Integer[][]> seatingArrangeMent = new ArrayList<Integer[][]>();

        for (int i = 0; i < n; i++) {

            System.out.println("Enter row and column");

            int row = scan.nextInt();
            int col = scan.nextInt();

            if (maxRow < row) {
                maxRow = row;
            }

            totalSeats = totalSeats + (row * col);

            Integer[][] arr = new Integer[row][col];

            for (int p = 0; p < row; p++) {
                for (int q = 0; q < col; q++) {
                    arr[p][q] = -1;
                }
            }
            seatingArrangeMent.add(arr);

        }
        System.out.println("Enter total number of passengers");
        int tolatPassengers = scan.nextInt();

        allocateSeats(seatingArrangeMent, tolatPassengers, totalSeats, scan, maxRow);

    }

    private static void allocateSeats(List<Integer[][]> seatingArrangeMent, int totalPassengers, int totalSeats,
                                      Scanner scan, int maxRow) {

        if (totalPassengers == 0 || totalSeats == 0) {
            System.out.println("Allocating seats not possible");
            return;
        }
        if (totalPassengers > totalSeats) {
            System.out.println(
                    "Total number of passengers are greater than seats.Press 1 to continue or press 2 to abort");
            int option = scan.nextInt();

            if (option == 1) {
                allocateSeatsImpl(seatingArrangeMent, totalPassengers, maxRow);
            } else if (option == 2) {
                return;
            }
        } else {
            allocateSeatsImpl(seatingArrangeMent, totalPassengers, maxRow);
        }

    }

    private static void allocateSeatsImpl(List<Integer[][]> seatingArrangeMent, int tolatPassengers, int maxRow) {

        int counter = tolatPassengers;

        counter = fillAisleSeats(counter, seatingArrangeMent, maxRow);

        if (counter > 0) {
            counter = fillWindowSeats(counter, seatingArrangeMent, maxRow,tolatPassengers);
        }
        if (counter > 0) {
            counter = fillCenterSeats(counter, seatingArrangeMent, maxRow, tolatPassengers);
        }

        for (int i = 0; i < seatingArrangeMent.size(); i++) {
            for (int j = 0; j < seatingArrangeMent.get(i).length; j++) {
                for (int p = 0; p < seatingArrangeMent.get(i)[0].length; p++) {
                    System.out.print(seatingArrangeMent.get(i)[j][p] + " ");

                }

                System.out.println();
            }

            System.out.println();
        }

    }

    private static int fillCenterSeats(int counter, List<Integer[][]> seatingArrangeMent, int maxRow,
                                       int tolatPassengers) {
        int index = tolatPassengers - counter + 1;
        for (int j = 0; j < maxRow; j++) {
            for (int i = 0; i < seatingArrangeMent.size(); i++) {

                if (seatingArrangeMent.get(i)[0].length > 2 && seatingArrangeMent.get(i).length > j) {

                    for (int p = 1; p < seatingArrangeMent.get(i)[0].length - 1; p++) {

                        if (counter <= 0) {
                            return counter;
                        }

                        seatingArrangeMent.get(i)[j][p] = index;
                        index++;
                        counter--;

                    }

                }
            }

        }

        return counter;
    }

    private static int fillWindowSeats(int counter, List<Integer[][]> seatingArrangeMent, int maxRow,
                                       int tolatPassengers) {

        int index = tolatPassengers - counter + 1;
        for (int j = 0; j < maxRow; j++) {

            if (counter <= 0) {
                return counter;
            }

            if (seatingArrangeMent.size() == 1) {
                //if there is only one matrix to be filled
                if (seatingArrangeMent.get(0)[0].length < 2) {

                    //if there is only one matrix and one column
                    seatingArrangeMent.get(0)[j][0] = index;
                    index++;
                    counter--;
                } else {
                    //if there is only one matrix and more than one column
                    seatingArrangeMent.get(0)[j][0] = index;
                    index++;
                    counter--;

                    if (counter > 0) {

                        seatingArrangeMent.get(0)[j][seatingArrangeMent.get(0)[j].length - 1] = index;
                        counter--;
                        index++;
                    }
                }
            } else {
                //We are filling the 0th index and last index if there are more than one matrix


                if (seatingArrangeMent.get(0).length > j) {
                    seatingArrangeMent.get(0)[j][0] = index;
                    index++;
                    counter--;
                }
                if (seatingArrangeMent.get(seatingArrangeMent.size()-1).length > j) {
                    if (counter > 0) {
                        seatingArrangeMent.get(seatingArrangeMent.size()-1)[j][seatingArrangeMent.get(seatingArrangeMent.size()-1)[j].length - 1] = index;
                        index++;
                        counter--;
                    }
                }


            }

        }

        return counter;
    }

    private static int fillAisleSeats(int counter, List<Integer[][]> seatingArrangeMent, int maxRow) {
        int index = 1;
        //if there is only one matrix then there is no possibility for aisle seats
        if (seatingArrangeMent.size() == 1) {
            return counter;
        }
        for (int j = 0; j < maxRow; j++) {

            for (int i = 0; i < seatingArrangeMent.size(); i++) {
                //for every row and every matrix
                if (counter <= 0) {
                    return counter;
                }
                //considering 1st matrix
                if (i == 0 && seatingArrangeMent.get(i)[0].length > 1) {
                    // Integer[][] temp = seatingArrangeMent.get(i);
                    int tempRow = seatingArrangeMent.get(i).length;
                    if (tempRow > j) {
                        int tempCol = seatingArrangeMent.get(i)[0].length;
                        seatingArrangeMent.get(i)[j][tempCol - 1] = index;
                        // temp[j][tempCol-1] = index;
                        index++;
                        counter--;

                    } else {
                        continue;
                    }
                    //considering last matrix
                } else if (i == seatingArrangeMent.size() - 1 && seatingArrangeMent.get(i)[0].length > 1) {

                    int tempRow = seatingArrangeMent.get(i).length;
                    if (tempRow > j) {
                        seatingArrangeMent.get(i)[j][0] = index;
                        // temp[j][tempCol-1] = index;
                        index++;
                        counter--;

                    } else {
                        continue;
                    }
                    //considering matrix in the middle
                } else {

                    if (seatingArrangeMent.get(i)[0].length == 1) {
                        int tempRow = seatingArrangeMent.get(i).length;
                        if (tempRow > j) {
                            seatingArrangeMent.get(i)[j][0] = index;
                            // temp[j][tempCol-1] = index;
                            index++;
                            counter--;

                        } else {
                            continue;
                        }
                    } else {

                        int tempRow = seatingArrangeMent.get(i).length;
                        if (tempRow > j) {
                            int tempCol = seatingArrangeMent.get(i)[0].length;
                            seatingArrangeMent.get(i)[j][0] = index;
                            // temp[j][tempCol-1] = index;
                            index++;
                            counter--;

                            // what if all passengers allocted

                            if (counter > 0) {

                                seatingArrangeMent.get(i)[j][tempCol - 1] = index;
                                counter--;
                                index++;
                            }

                        } else {
                            continue;
                        }

                    }

                }

            }
        }

        return counter;
    }

}
