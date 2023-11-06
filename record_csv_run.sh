#!/usr/bin/env bash
GREEN='\033[0;32m'
NC='\033[0m'

echo "Generating 2 CSV files, one with accounts, one with transactions"
sleep 1
./run.sh CsvPlan

echo "Let's get one account_id and check there are corresponding transactions for it"
sleep 1
account_line=$(tail -1 docker/sample/customer/account/part-00000-*)
account_id=$(echo "$account_line" | awk -F "," '{print $1}')
echo -e "${GREEN}Account record${NC}: $account_line"
echo -e "${GREEN}Using account_id${NC}=$account_id"
transaction_lines=$(cat docker/sample/customer/transaction/part-* | grep "$account_id")
echo -e "${GREEN}Corresponding transactions: ${NC}"
echo "$transaction_lines"
