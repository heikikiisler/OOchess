package moves;

public class Move {

    int sr;
    int sc;
    int er;
    int ec;
    char ep;

    public Move(int sr, int sc, int er, int ec) {
        this.sr = sr;
        this.sc = sc;
        this.er = er;
        this.ec = ec;
    }

    public int getSr() {
        return sr;
    }

    public int getSc() {
        return sc;
    }

    public int getEr() {
        return er;
    }

    public int getEc() {
        return ec;
    }
}
