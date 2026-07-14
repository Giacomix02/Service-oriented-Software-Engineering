export interface Artist {
    id: number;
    name: string;
    description: string;
}

export interface Song {
    id: number;
    name: string;
    description: string;
    views: number;
    artist: Artist;
    streamingServices: StreamingService[]
}

export interface StreamingService {
    id: number;
    name: string;
    description: string
}

export interface TicketingEvent {
    id: number;
    eventGlobalId: number;
    name: string;
    artistName: string;
    location: string;
    description: string;
}

export interface TicketOffer {
    id: number;
    price: number;
    seat: string;
    eventId: number;
    source: "OFFICIAL" | "RESELLER";
}

export interface TicketComparison {
    eventGlobalId: number;
    officialTickets: TicketOffer[];
    resaleTickets: TicketOffer[];
    cheapestOverall: TicketOffer | null;
}

export type ResellerFilter = "OFFICIAL" | "RESELLER" | "BOTH";