#!/bin/bash

mvn compile && mvn test && mvn jacoco:report && mvn jxr:jxr checkstyle:checkstyle && echo "All done."

