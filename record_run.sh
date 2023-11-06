#!/usr/bin/env bash

asciinema rec -c "./record_csv_run.sh" --idle-time-limit=2 --overwrite csv_generation_run.cast
agg --no-loop csv_generation_run.cast csv_generation_run.gif
