package model;

public class Location {

    private int firstCo;
    private int secondCo;

    public Location(int firstCo, int secondCo) {
        this.firstCo = firstCo;
        this.secondCo = secondCo;
    }

    public double travelTime(int firstCorOther, int secondCorOther){
        double firstSum = 0;
        double secondsum = 0;

        if (firstCo < firstCorOther){
            firstSum = firstCorOther - firstCo;
        }else {
            firstSum = firstCo - firstCorOther;
        }

        if (secondCo < secondCorOther){
            secondsum = secondCorOther - secondCo;
        }else {
            secondsum = secondCo - secondCorOther;
        }


        double realSum = firstSum + secondsum;



        return realSum * 1.5;
    }


    public int getFirstCo() {
        return firstCo;
    }

    public void setFirstCo(int firstCo) {
        this.firstCo = firstCo;
    }

    public int getSecondCo() {
        return secondCo;
    }

    public void setSecondCo(int secondCo) {
        this.secondCo = secondCo;
    }

    @Override
    public String toString() {
        return "Location{" +
                "firstCo=" + firstCo +
                ", secondCo=" + secondCo +
                '}';
    }
}
