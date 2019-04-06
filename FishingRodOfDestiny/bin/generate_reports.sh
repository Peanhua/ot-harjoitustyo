#!/bin/bash

mvn compile && mvn test && mvn jacoco:report && echo "All done."

