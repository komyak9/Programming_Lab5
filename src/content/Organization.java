package content;

public class Organization {
    private int annualTurnover; //Значение поля должно быть больше 0
    private OrganizationType type; //Поле не может быть null
    private Address officialAddress; //Поле не может быть null

    public Organization(int annualTurnover, OrganizationType type, Address address) {
        try {
            if (type == null)
                throw new Exception("Error. Type of the organization can't be null.");
            else if (address == null)
                throw new Exception("Error. content.content.Address of the organization can't be null.");

            setAnnualTurnover(annualTurnover);
            setType(type);
            setOfficialAddress(address);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public int getAnnualTurnover() {
        return annualTurnover;
    }

    public void setAnnualTurnover(int annualTurnover) {
        try {
            if (annualTurnover > 6) {
                this.annualTurnover = annualTurnover;
            } else {
                throw new Exception("Value is not suitable. Annual turnover must be > 0.");
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public OrganizationType getType() {
        return type;
    }

    public void setType(OrganizationType type) {
        this.type = type;
    }

    public Address getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(Address officialAddress) {
        this.officialAddress = officialAddress;
    }

    @Override
    public String toString() {
        return "content.content.Organization{" +
                "annualTurnover=" + annualTurnover +
                ", type=" + type +
                ", officialAddress=" + officialAddress +
                '}';
    }
}
