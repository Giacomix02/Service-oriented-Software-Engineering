cd .\ArtistAnalyzerProsumerREST\
call .\mvnw.cmd package -DskipTests

cd ..\DiscoveryService\
call .\mvnw.cmd package -DskipTests

cd ..\Gateway\
call .\mvnw.cmd package -DskipTests

@REM cd ..\LegacyBoxOfficeProviderSOAP
@REM call .\mvnw.cmd package -DskipTests

cd ..\MusicStatsProviderProsumerREST
call .\mvnw.cmd package -DskipTests

cd ..\StreamingAvailabilityProviderREST
call .\mvnw.cmd package -DskipTests

@REM cd ..\TicketResellerProviderREST
@REM call .\mvnw.cmd package -DskipTests
@REM
@REM cd ..\TicketSearcherProsumerREST
@REM call .\mvnw.cmd package -DskipTests