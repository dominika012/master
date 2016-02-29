package com.master.gauss.core;

import java.io.Serializable;

/**
 * Created by Dominika on 8. 10. 2015.
 */
public class Fraction implements Serializable {

    public static final String FEXTRA = "com.example.dominika.gauss.fextras";

    private int numerator, denominator;

    public Fraction()
    {
        numerator = denominator = 0;
    }

    public Fraction(int numerator, int denominator){
        this.numerator = numerator;
        this.denominator = denominator;
    }

    public int getNumerator()
    {
        return numerator;
    }

    public void setNumerator(int num)
    {
        numerator=num;
    }

    public int getDenominator()
    {
        return denominator;
    }

    public void setDenominator(int den)
    {
        denominator=den;
    }



    /****************************************************/
   /* public action methods for manipulating fractions */
    /****************************************************/

    /**********************************************************
     Method:         add
     Purpose:        Add two fractions, a and b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     b, the fraction to add to "this"
     Preconditions:  Both fractions a and b must contain valid values
     Postconditions: None
     Returns:        A Fraction representing the sum of two
     fractions a & b
     ***********************************************************/
    public Fraction add(Fraction b)
    {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator - add");
        // find lowest common denominator
        int common = lcd(denominator, b.denominator);
        // convert fractions to lcd
        Fraction commonA = new Fraction();
        Fraction commonB = new Fraction();
        commonA = convert(common);
        commonB = b.convert(common);
        // create new fraction to return as sum
        Fraction sum = new Fraction();
        // calculate sum
        sum.numerator = commonA.numerator + commonB.numerator;
        sum.denominator = common;
        // reduce the resulting fraction
        sum = sum.reduce();
        return sum;
    }

    /**********************************************************
     Method:         subtract
     Purpose:        Subtract fraction b from a, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     b, the fraction to subtract from "this"
     Preconditions:  Both fractions a and b must contain valid values
     Postconditions: None
     Returns:        A Fraction representing the differenct of the
     two fractions a & b
     ***********************************************************/
    public Fraction subtract(Fraction b)
    {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator - subtract");
        // find lowest common denominator
        int common = lcd(denominator, b.denominator);
        // convert fractions to lcd
        Fraction commonA = new Fraction();
        Fraction commonB = new Fraction();
        commonA = convert(common);
        commonB = b.convert(common);
        // create new fraction to return as difference
        Fraction diff = new Fraction();
        // calculate difference
        diff.numerator = commonA.numerator - commonB.numerator;
        diff.denominator = common;
        // reduce the resulting fraction
        diff = diff.reduce();
        return diff;
    }

    /**********************************************************
     Method:         multiply
     Purpose:        Multiply fractions a and b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     The fraction b to multiply "this" by
     Preconditions:  Both fractions a and b must contain valid values
     Postconditions: None
     Returns:        A Fraction representing the product of the
     two fractions a & b
     ***********************************************************/
    public Fraction multiply(Fraction b)
    {
        // check preconditions
        if ((denominator == 0) || (b.denominator == 0))
            throw new IllegalArgumentException("invalid denominator - multiply");
        // create new fraction to return as product
        Fraction product = new Fraction();
        // calculate product
        product.numerator = numerator * b.numerator;
        product.denominator = denominator * b.denominator;
        // reduce the resulting fraction
        product = product.reduce();
        return product;
    }

    /**********************************************************
     Method:         divide
     Purpose:        Divide fraction a by b, where a is the "this"
     object, and b is passed as the input parameter
     Parameters:     The fraction b to divide "this" by
     Preconditions:  Both fractions a and b must contain valid values
     Postconditions: None
     Returns:        A Fraction representing the result of dividing
     fraction a by b
     ***********************************************************/
    public Fraction divide(Fraction b)
    {
        // check preconditions
        if ((denominator == 0) || (b.numerator == 0))
            throw new IllegalArgumentException("invalid denominator - divide");
        // create new fraction to return as result
        Fraction result = new Fraction();
        // calculate result
        result.numerator = numerator * b.denominator;
        result.denominator = denominator * b.numerator;
        // reduce the resulting fraction
        result = result.reduce();
        return result;
    }

    public boolean isPositive(){
        if(this.getNumerator()>0)
            return true;
        return false;
    }

    public boolean isZero(){
        if(this.getNumerator()==0)
            return true;
        return false;
    }

    public String toString()
    {
        String buffer="";

        if (denominator==1)
            buffer = numerator+"";
        else
            buffer = numerator + "/" + denominator;
        return buffer;
    }

    /*****************************************************/
   /* private methods used internally by Fraction class */
    /*****************************************************/

    /**********************************************************
     Method:         lcd
     Purpose:        find lowest common denominator, used to add and
     subtract fractions
     Parameters:     two integers, denom1 and denom2
     Preconditions:  denom1 and denom2 must be non-zero integer values
     Postconditions: None
     Returns:        the lowest common denominator between denom1 and
     denom2
     ***********************************************************/
    private int lcd(int denom1, int denom2)
    {
        int factor = denom1;
        while ((denom1 % denom2) != 0)
            denom1 += factor;
        return denom1;
    }

    /**********************************************************
     Method:         gcd
     Purpose:        find greatest common denominator, used to reduce
     fractions
     Parameters:     two integers, denom1 and denom2
     Preconditions:  denom1 and denom2 must be positive integer values
     denom1 is assumed to be greater than denom2
     (denom1 > denom2 > 0)
     Postconditions: None
     Returns:        the greatest common denominator between denom1 and
     denom2
     Credits:        Thanks to Euclid for inventing the gcd algorithm,
     and to Prof. Joyce for explaining it to me.
     ***********************************************************/
    private int gcd(int denom1, int denom2)
    {
        int factor = denom2;
        while (denom2 != 0) {
            factor = denom2;
            denom2 = denom1 % denom2;
            denom1 = factor;
        }
        return denom1;
    }

    /**********************************************************
     Method:         convert
     Purpose:        convert a fraction to an equivalent one based on
     a lowest common denominator
     Parameters:     an integer common, the new denominator
     Preconditions:  the "this" fraction must contain valid data for
     numerator and denominator
     the integer value common is assumed to be greater
     than the "this" fraction's denominator
     Postconditions: None
     Returns:        A new fraction which is equivalent to the "this"
     fraction, but has been converted to the new
     denominator called common
     ***********************************************************/
    private Fraction convert(int common)
    {
        Fraction result = new Fraction();
        int factor = common / denominator;
        result.numerator = numerator * factor;
        result.denominator = common;
        return result;
    }

    /**********************************************************
     Method:         reduce
     Purpose:        convert the "this" fraction to an equivalent one
     based on a greatest common denominator
     Parameters:     None
     Preconditions:  The "this" fraction must contain valid data for
     numerator and denominator
     Postconditions: None
     Returns:        A new fraction which is equivalent to a, but has
     been reduced to its lowest numerical form
     ***********************************************************/
    private Fraction reduce()
    {
        Fraction result = new Fraction();
        int common = 0;
        // get absolute values for numerator and denominator
        int num = numerator;
        int den = denominator;
        // figure out which is less, numerator or denominator



        if (Math.abs(num) > Math.abs(den))
            common = gcd(num, den);
        else if (Math.abs(num) <  Math.abs(den))
            common = gcd(den, num);
        else  // if both are the same, don't need to call gcd
            common = num;

        // set result based on common factor derived from gcd
        result.numerator = numerator / common;
        result.denominator = denominator / common;

        if (result.denominator<0){
            result.denominator=result.denominator*(-1);
            result.numerator=result.numerator*(-1);
        }

        return result;
    }



}