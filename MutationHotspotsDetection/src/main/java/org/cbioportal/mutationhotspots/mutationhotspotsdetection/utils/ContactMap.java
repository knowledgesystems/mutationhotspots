/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cbioportal.mutationhotspots.mutationhotspotsdetection.utils;

/**
 *
 * @author jgao
 */
public class ContactMap {
    private boolean[][] contact;
    private int proteinLeft, proteinRight;

    ContactMap(int len) {
        contact = new boolean[len+1][len+1];
    }

    public boolean[][] getContact() {
        return contact;
    }

    public int getProteinLeft() {
        return proteinLeft;
    }

    public void setProteinLeft(int proteinLeft) {
        this.proteinLeft = proteinLeft;
    }

    public int getProteinRight() {
        return proteinRight;
    }

    public void setProteinRight(int proteinRight) {
        this.proteinRight = proteinRight;
    }
}
