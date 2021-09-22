package com.mybid.models;

public class Product {
    private String pid;
    private String pname;
    private String pselid;
    private String pbuyid;
    private Integer pstatus;
    private Integer pcondition;
    private String plocation;
    private String pcategory;
    private Double pstartprice;
    private Double psoldprice;
    private String pdesc;
    private byte[] pimgByte;

    public Product(){
    }

    public Product(String pid, String pname, String pselid, String pbuyid, Integer pstatus, Integer pcondition,
                   String plocation, String pcategory, Double pstartprice, Double psoldprice, String pdesc, byte[] pimgByte) {
        this.pid = pid;
        this.pname = pname;
        this.pselid = pselid;
        this.pbuyid = pbuyid;
        this.pstatus = pstatus;
        this.pcondition = pcondition;
        this.plocation = plocation;
        this.pcategory = pcategory;
        this.pstartprice = pstartprice;
        this.psoldprice = psoldprice;
        this.pdesc = pdesc;
        this.pimgByte = pimgByte;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPselid() {
        return pselid;
    }

    public void setPselid(String pselid) {
        this.pselid = pselid;
    }

    public String getPbuyid() {
        return pbuyid;
    }

    public void setPbuyid(String pbuyid) {
        this.pbuyid = pbuyid;
    }

    public Integer getPstatus() {
        return pstatus;
    }

    public void setPstatus(Integer pstatus) {
        this.pstatus = pstatus;
    }

    public Integer getPcondition() {
        return pcondition;
    }

    public void setPcondition(Integer pcondition) {
        this.pcondition = pcondition;
    }

    public String getPlocation() {
        return plocation;
    }

    public void setPlocation(String plocation) {
        this.plocation = plocation;
    }

    public String getPcategory() {
        return pcategory;
    }

    public void setPcategory(String pcategory) {
        this.pcategory = pcategory;
    }

    public Double getPstartprice() {
        return pstartprice;
    }

    public void setPstartprice(Double pstartprice) {
        this.pstartprice = pstartprice;
    }

    public Double getPsoldprice() {
        return psoldprice;
    }

    public void setPsoldprice(Double psoldprice) {
        this.psoldprice = psoldprice;
    }

    public String getPdesc() {
        return pdesc;
    }

    public void setPdesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public byte[] getPimgByte() {
        return pimgByte;
    }

    public void setPimgByte(byte[] pimgByte) {
        this.pimgByte = pimgByte;
    }
}
