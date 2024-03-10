package org.androsovich.applications.entities.enums;

public enum StatusType {
    DRAFT {
        @Override
        public StatusType prevStatus() {
            return this;
        }
    },

    SENT {
        @Override
        public StatusType prevStatus() {
            return DRAFT;
        }
    },

    ACCEPTED {
        @Override
        public StatusType prevStatus() {
            return SENT;
        }
    },

    REJECTED {
        @Override
        public StatusType prevStatus() {
            return SENT;
        }
    };
    public abstract StatusType prevStatus();
}
