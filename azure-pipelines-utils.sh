#!/bin/bash
function retry {
  local max_attempts=5
  local timeout=1
  local attempt=1
  local exitCode=0

  while (( $attempt < $max_attempts ))
  do
    if "$@"
    then
      return 0
    else
      exitCode=$?
    fi

    echo ">>> Operation error, retrying in $timeout" 1>&2
    sleep $timeout
    attempt=$(( attempt + 1 ))
    timeout=$(( timeout * 2 ))
  done

  if [[ $exitCode != 0 ]]
  then
    echo ">>> Operation failed ($@)" 1>&2
  fi

  return $exitCode;
} 