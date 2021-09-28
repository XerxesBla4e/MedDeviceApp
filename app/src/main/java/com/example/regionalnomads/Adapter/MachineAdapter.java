package com.example.regionalnomads.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.regionalnomads.Model.MachinesModel;
import com.example.regionalnomads.R;
import com.example.regionalnomads.Users.DatabaseManger;


public class MachineAdapter extends RecyclerView.Adapter<MachineAdapter.ViewHolder> {
    String imageUri;
    DatabaseManger databaseManager;

    Context context;
    java.util.List<MachinesModel> machinesModelList;

    public MachineAdapter(Context context, java.util.List<MachinesModel> machinesModelList) {
        this.context = context;
        this.machinesModelList = machinesModelList;
    }

    @androidx.annotation.NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        android.view.View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.machinerow, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MachinesModel machinesModel = machinesModelList.get(position);

        holder.tvmid.setText(String.valueOf(machinesModel.getId()));
        holder.tvmname.setText(machinesModel.getName());
        holder.tvmserial.setText(machinesModel.getSerialnum());
        holder.tvmmodel.setText(machinesModel.getModelnum());
        holder.tvmmanf.setText(machinesModel.getManufacturer());
        holder.tvmdepartment.setText(machinesModel.getDepartment());
        holder.tvmstate.setText(machinesModel.getMachine_state());
        holder.tvmservice.setText(machinesModel.getLastservice());
        holder.tvmcomment.setText(machinesModel.getComment());

    }

    @Override
    public int getItemCount() {
        return machinesModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        android.widget.TextView tvmid, tvmname, tvmserial, tvmmodel, tvmmanf, tvmdepartment, tvmstate, tvmservice, tvmcomment;

        public ViewHolder(android.view.View itemView) {
            super(itemView);
            tvmid = itemView.findViewById(R.id.mid);
            tvmname = itemView.findViewById(R.id.mname);
            tvmserial = itemView.findViewById(R.id.mserial);
            tvmmodel = itemView.findViewById(R.id.mnumber);
            tvmmanf = itemView.findViewById(R.id.mmanf);
            tvmdepartment = itemView.findViewById(R.id.mdepartment);
            tvmstate = itemView.findViewById(R.id.mstate);
            tvmservice = itemView.findViewById(R.id.mlastservice);
            tvmcomment = itemView.findViewById(R.id.rcomment);
        }
    }

}
