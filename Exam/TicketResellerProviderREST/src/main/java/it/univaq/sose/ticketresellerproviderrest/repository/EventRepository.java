package it.univaq.sose.ticketresellerproviderrest.repository;

public interface EventRepository {
    boolean existsByGlobalId(int eventGlobalId);
}