package goldenheavenhotel.model;

public class VipGuests extends Guests {
    private boolean premiumWiFi;
    private boolean complimentaryMeal;
    private boolean spaAccess;

    public String getExecutiveSuite() {
        return premiumWiFi ? "Yes" : "No";
    }
    public void setPremiumWiFi(boolean premiumWiFi) {
        this.premiumWiFi = premiumWiFi;
    }

    public String getComplimentaryMeal() {
        return complimentaryMeal ? "Yes" : "No";
    }
    public void setComplimentaryMeal(boolean complimentaryMeal) {
        this.complimentaryMeal = complimentaryMeal;
    }

    public String getSpaAccess() {
        return spaAccess ? "Yes" : "No";
    }
    public void setSpaAccess(boolean spaAccess) {
        this.spaAccess = spaAccess;
    }

    public boolean isPremiumWiFi() {
        return premiumWiFi;
    }
    public boolean isComplimentaryMeal() {
        return complimentaryMeal;
    }
    public boolean isSpaAccess() {
      return spaAccess;
    }


}

