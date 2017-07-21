package com.t_nishikawa.internrssreaderapp;

import android.util.Log;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class RssParser {
    private static final String TAG = RssParser.class.getSimpleName();

    private XmlPullParser parser;

    public RssParser() {
        XmlPullParserFactory factory;
        try {
            factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            this.parser = factory.newPullParser();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
    }

    public List<RssListItem> parse(String rssData) {
        if (parser == null) {
            return new ArrayList<>();
        }

        ArrayList<RssListItem> rssListItems = new ArrayList<>();
        try {
            parser.setInput(new StringReader(rssData));
            int eventType = parser.getEventType();
            RssListItem rssListItem = null;
            String text = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_DOCUMENT) {
                } else if (eventType == XmlPullParser.START_TAG) {
                    final String tagName = parser.getName();
                    switch (tagName) {
                        case "item":
                            rssListItem = new RssListItem();
                            break;
                    }
                } else if (eventType == XmlPullParser.TEXT) {
                    text = parser.getText();
                } else if (eventType == XmlPullParser.END_TAG) {
                    final String tagName = parser.getName();
                    switch (tagName) {
                        case "item":
                            rssListItems.add(rssListItem);
                            rssListItem = null;
                            break;
                        case "title":
                            if (rssListItem != null) {
                                rssListItem.setTitle(text);
                                text = null;
                            }
                            break;
                        case "description":
                            if (rssListItem != null) {
                                rssListItem.setSubText(text);
                                text = null;
                            }
                            break;
                    }
                }
                eventType = parser.next();
            }
        } catch (XmlPullParserException | IOException e) {
            e.printStackTrace();
        }

        return rssListItems;
    }
}
