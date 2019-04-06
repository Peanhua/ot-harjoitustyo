#!/bin/bash

mvn clean && mvn compile && mvn test && mvn jacoco:report

