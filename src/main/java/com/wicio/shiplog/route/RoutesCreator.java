package com.wicio.shiplog.route;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
class RoutesCreator {

  private final InitialLogsForVesselsCreator initialLogsForVesselsCreator;
  private final SubsequentLogsForVesselsCreator subsequentLogsForVesselsCreator;

  void createRoutesForVessels() {
    initialLogsForVesselsCreator.execute();
    subsequentLogsForVesselsCreator.execute();
  }

}
