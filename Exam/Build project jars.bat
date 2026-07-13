cd .\ArtistAnalyzerProsumerREST\
call .\mvnw.cmd package -DskipTests

cd ..\DiscoveryService\
call .\mvnw.cmd package -DskipTests

cd ..\Gateway\
call .\mvnw.cmd package -DskipTests

cd ..\LegacyBoxOfficeProviderSOAP
call .\mvnw.cmd package -DskipTests

cd ..\MusicStatsProviderProsumerREST
call .\mvnw.cmd package -DskipTests

cd ..\StreamingAvailabilityProviderREST
call .\mvnw.cmd package -DskipTests

cd ..\TicketResellerProviderREST
call .\mvnw.cmd package -DskipTests

@REM cd ..\TicketSearcherProsumerREST
@REM call .\mvnw.cmd package -DskipTests