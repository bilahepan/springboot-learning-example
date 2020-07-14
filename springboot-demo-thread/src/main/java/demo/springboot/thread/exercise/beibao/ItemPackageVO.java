package demo.springboot.thread.exercise.beibao;

public class ItemPackageVO {
    //箱包号
    private String sn = "";
    //规格
    private int spec;
    //数量
    private int num;
    //批次
    private int batch;

    public ItemPackageVO() {
    }

    public ItemPackageVO(String sn, int spec, int num, int batch) {
        this.sn = sn;
        this.spec = spec;
        this.num = num;
        this.batch = batch;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }

    public int getSpec() {
        return spec;
    }

    public void setSpec(int spec) {
        this.spec = spec;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getBatch() {
        return batch;
    }

    public void setBatch(int batch) {
        this.batch = batch;
    }

    @Override
    public String toString() {
        return "ItemPackageVO{" +
                "sn='" + sn + '\'' +
                ", spec=" + spec +
                ", num=" + num +
                ", batch=" + batch +
                '}';
    }
}
