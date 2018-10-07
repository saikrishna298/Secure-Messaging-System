package securemessagingsystem;
/***********************************
 * Secure Messaging System
 * CIS 535 Project 1
 * Sai Krishna Ganji
 * RSAAlgorithm.java
 **********************************/

import java.math.BigInteger;
import java.util.Random;

public class RSAAlgorithm {

    private BigInteger p, q, n, z, e, d;

    public RSAAlgorithm() {
        p = new BigInteger("5");
        q = new BigInteger("11");
        n = new BigInteger("55");
        z = new BigInteger("40");
        e = new BigInteger("3");
        d = new BigInteger("27");
    }

    public void generateKeys() {

        Random r = new Random();
        int bitlength = 16;

        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        n = p.multiply(q);
        z = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));

        e = BigInteger.probablePrime(bitlength / 2, r);

        //changed e.add(BigInteger.ONE); to e = e.add(BigInteger.ONE);
        while (z.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(z) < 0) {
            e = e.add(BigInteger.ONE);
        }
        d = e.modInverse(z);

    }

    public BigInteger encryption(String m) {

        BigInteger biMessage = new BigInteger(m);

        return biMessage.modPow(e, n);

    }

    public BigInteger decryption(String c) {

        BigInteger biCipher = new BigInteger(c);

        return biCipher.modPow(d, n);

    }

    /**
     * @return the p
     */
    public BigInteger getP() {
        return p;
    }

    /**
     * @param p the p to set
     */
    public void setP(BigInteger p) {
        this.p = p;
    }

    /**
     * @return the q
     */
    public BigInteger getQ() {
        return q;
    }

    /**
     * @param q the q to set
     */
    public void setQ(BigInteger q) {
        this.q = q;
    }

    /**
     * @return the n
     */
    public BigInteger getN() {
        return n;
    }

    /**
     * @param n the n to set
     */
    public void setN(BigInteger n) {
        this.n = n;
    }

    /**
     * @return the z
     */
    public BigInteger getZ() {
        return z;
    }

    /**
     * @param z the z to set
     */
    public void setZ(BigInteger z) {
        this.z = z;
    }
    
    /**
     * @return the d
     */
    public BigInteger getD() {
        return d;
    }
    
    /**
     * @param z the z to set
     */
    public void setD(BigInteger d) {
        this.d = d;
    }
    
    /**
     * @return the e
     */
    public BigInteger getE() {
        return e;
    }
    
    /**
     * @param e the e to set
     */
    public void setE(BigInteger e) {
        this.e = e;
    }
}