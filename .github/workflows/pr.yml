name: PR Check

on:
  pull_request:
    types: [opened, edited, synchronize]

jobs:
  check_pr:
    name: PR Description Check
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Run PR body check
        env:
          BODY: ${{ toJson(github.event.pull_request.body) }}
        run: |
          if [[ "$BODY" =~ "TODO" ]]; then
            echo "Remaining TODO in description"
            exit 1
          fi
