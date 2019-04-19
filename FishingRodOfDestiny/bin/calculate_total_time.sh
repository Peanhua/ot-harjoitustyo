#!/bin/bash

MATH=$(for i in $(grep -E '^  <tr><td>' ../documentation/timesheet.md | cut --characters=25-29)
       do
           echo -n "${i}+"
       done
       echo "0"
    )
echo ${MATH}
echo ${MATH} | bc -l
