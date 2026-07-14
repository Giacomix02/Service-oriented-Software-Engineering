import { TicketingEvent, TicketOffer, TicketComparison, ResellerFilter } from "@/types"
import axios from "axios"
import { Dispatch, SetStateAction } from "react"

const baseURL = "/api-gateway"

/// GET ticket-searcher/events
export const getAllTicketingEvents = async (setEvents: Dispatch<SetStateAction<TicketingEvent[]>>) => {
    const response = await axios.get(baseURL + "/ticket-searcher/events")
    const events: TicketingEvent[] = await response.data
    setEvents(events)
}

/// GET ticket-searcher/events?name={name}
export const searchTicketingEventsByName = async (name: string, setEvents: Dispatch<SetStateAction<TicketingEvent[]>>) => {
    const response = await axios.get(baseURL + "/ticket-searcher/events", { params: { name } })
    const events: TicketingEvent[] = await response.data
    setEvents(events)
}

/// GET ticket-searcher/events/{eventGlobalId}/tickets
export const getTicketComparison = async (eventGlobalId: number, setComparison: Dispatch<SetStateAction<TicketComparison | undefined>>) => {
    const response = await axios.get(baseURL + `/ticket-searcher/events/${eventGlobalId}/tickets`)
    const comparison: TicketComparison = await response.data
    setComparison(comparison)
}

/// GET ticket-searcher/events/{eventGlobalId}/tickets/cheapest
export const getCheapestTicket = async (eventGlobalId: number, setOffer: Dispatch<SetStateAction<TicketOffer | undefined>>) => {
    const response = await axios.get(baseURL + `/ticket-searcher/events/${eventGlobalId}/tickets/cheapest`)
    const offer: TicketOffer = await response.data
    setOffer(offer)
}

/// POST ticket-searcher/search
export const searchTicketsByCriteria = async (
    eventName: string,
    resellerType: ResellerFilter,
    setComparison: Dispatch<SetStateAction<TicketComparison | undefined>>
) => {
    const response = await axios.post(baseURL + "/ticket-searcher/search", {
        eventName,
        resellerType,
    })

    const data = response.data
    if (!data || Object.keys(data).length === 0) {
        setComparison(undefined)
        return
    }

    const comparison: TicketComparison = data
    setComparison(comparison)
}