#!/usr/bin/env bash

asciinema rec -c "./record_http_run.sh" --idle-time-limit=2 --overwrite http_generation_run.cast
agg --no-loop http_generation_run.cast http_generation_run.gif
