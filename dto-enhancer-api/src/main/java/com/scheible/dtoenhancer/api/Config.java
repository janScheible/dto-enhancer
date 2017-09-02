package com.scheible.dtoenhancer.api;

import java.io.PrintStream;

/**
 *
 * @author sj
 */
public class Config {

    private String annotationClassFullName = AutoDto.class.getName();
    private boolean mandatoryThis = false;
    private boolean mandatoryFinal = false;
    private PrintStream out = System.out;
    private boolean verbose = true;
    private boolean verificationOnly = true;

    public void setAnnotationClassFullName(String annotationClassFullName) {
        this.annotationClassFullName = annotationClassFullName;
    }

    public void setMandatoryFinal(boolean mandatoryFinal) {
        this.mandatoryFinal = mandatoryFinal;
    }

    public void setMandatoryThis(boolean mandatoryThis) {
        this.mandatoryThis = mandatoryThis;
    }

    public void setOut(PrintStream out) {
        this.out = out;
    }

    public void setVerbose(boolean verbose) {
        this.verbose = verbose;
    }

    public void setVerificationOnly(boolean verificationOnly) {
        this.verificationOnly = verificationOnly;
    }

    public String getAnnotationClassFullName() {
        return annotationClassFullName;
    }

    public boolean isMandatoryFinal() {
        return mandatoryFinal;
    }

    public boolean isMandatoryThis() {
        return mandatoryThis;
    }

    public PrintStream getOut() {
        return out;
    }

    public boolean isVerbose() {
        return verbose;
    }

    public boolean isVerificationOnly() {
        return verificationOnly;
    }
}
