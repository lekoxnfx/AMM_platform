package com.amm.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;

/**
 * Created by csw on 2016/7/25 10:26.
 * Explain:
 */
@Entity
@Table(name = "mach_terminal", schema = "", catalog = "amm")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class MachTerminalEntity {
    private Integer id;
    private Date startTime;
    private Date endTime;
    private MachineEntity machineByMachId;
    private TerminalEntity terminalByTerminalId;
    private Collection<RefMachTerminalEntity> refMachTerminalsById;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Basic
    @Column(name = "start_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Basic
    @Column(name = "end_time")
    @Temporal(TemporalType.TIMESTAMP)
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MachTerminalEntity that = (MachTerminalEntity) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (startTime != null ? !startTime.equals(that.startTime) : that.startTime != null) return false;
        if (endTime != null ? !endTime.equals(that.endTime) : that.endTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "mach_id", referencedColumnName = "id", nullable = false)
    public MachineEntity getMachineByMachId() {
        return machineByMachId;
    }

    public void setMachineByMachId(MachineEntity machineByMachId) {
        this.machineByMachId = machineByMachId;
    }

    @ManyToOne
    @JoinColumn(name = "terminal_id", referencedColumnName = "id", nullable = false)
    public TerminalEntity getTerminalByTerminalId() {
        return terminalByTerminalId;
    }

    public void setTerminalByTerminalId(TerminalEntity terminalByTerminalId) {
        this.terminalByTerminalId = terminalByTerminalId;
    }

    @OneToMany(mappedBy = "machTerminalByMachTerminalId")
    @JsonIgnore
    public Collection<RefMachTerminalEntity> getRefMachTerminalsById() {
        return refMachTerminalsById;
    }

    public void setRefMachTerminalsById(Collection<RefMachTerminalEntity> refMachTerminalsById) {
        this.refMachTerminalsById = refMachTerminalsById;
    }
}
