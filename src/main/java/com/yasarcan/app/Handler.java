package com.yasarcan.app;

import com.yasarcan.app.StringApproximation;
import java.util.ArrayList;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class Handler
extends DefaultHandler {
    String firstName;
    String lastName;
    String inputFirstName;
    String inputLastName;
    int id;
    boolean readFirstName;
    boolean readLastName;
    boolean readEntity;
    int count = 0;
    ArrayList<String> result = new ArrayList();

    public void setInputFirstName(String str) {
        this.inputFirstName = str;
    }

    public void setInputLastName(String str) {
        this.inputLastName = str;
    }

    public ArrayList<String> returnResult() {
        return this.result;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("ENTITY")) {
            this.count = 0;
            this.readEntity = true;
            this.id = Integer.parseInt(attributes.getValue("Id"));
        } else if (qName.equals("LASTNAME")) {
            ++this.count;
            this.readLastName = true;
        } else if (qName.equals("FIRSTNAME")) {
            this.readFirstName = true;
        }
    }

    @Override
    public void endElement(String uri, String localname, String qName) throws SAXException {
        StringApproximation obj = new StringApproximation();
        if (qName.equals("ENTITY")) {
            if (this.inputFirstName != null && this.inputLastName != null) {
                if (obj.StringApprox(this.inputFirstName, this.firstName) && obj.StringApprox(this.inputLastName, this.lastName)) {
                    String temp = "<tr><td bgcolor=\"F3DF8E\"><strong>" + this.id + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.firstName + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.lastName + "</td>";
                    this.result.add(temp);
                }
            } else if (this.inputFirstName != null) {
                if (obj.StringApprox(this.inputFirstName, this.firstName)) {
                    String temp = "<tr><td bgcolor=\"F3DF8E\"><strong>" + this.id + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.firstName + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.lastName + "</td>";
                    this.result.add(temp);
                }
            } else if (this.inputLastName != null && obj.StringApprox(this.inputLastName, this.lastName)) {
                String temp = "<tr><td bgcolor=\"F3DF8E\"><strong>" + this.id + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.firstName + "</td><td bgcolor=\"F3DF8E\"><strong>" + this.lastName + "</td>";
                this.result.add(temp);
            }
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        String data = new String(ch, start, length);
        if (this.readFirstName && this.count == 1) {
            this.firstName = data;
            this.readFirstName = false;
        }
        if (this.readLastName && this.count == 1) {
            this.lastName = data;
            this.readLastName = false;
        }
        if (this.readEntity) {
            this.readEntity = false;
        }
    }
}