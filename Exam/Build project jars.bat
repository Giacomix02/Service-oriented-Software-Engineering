cd .\ArtistAnalyzerProsumerREST\
call .\mvnw.cmd package

cd ..\DiscoveryService\
call .\mvnw.cmd package

cd ..\Gateway\
call .\mvnw.cmd package

cd ..\LegacyBoxOfficeProviderSOAP
call .\mvnw.cmd package

cd ..\MusicStatsProviderProsumerREST
call .\mvnw.cmd package

cd ..\StreamingAvailabilityProviderREST
call .\mvnw.cmd package

cd ..\TicketResellerProviderREST
call .\mvnw.cmd package

cd ..\TicketSearcherProsumerREST
call .\mvnw.cmd package