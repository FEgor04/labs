import {Formik} from "formik";
import FormWrapper from "../../forms/FormWrapper";
import FormInput from "../../forms/FormInput";
import FormSelect from "../../forms/FormSelect";
import FormButton from "../../forms/FormButton";
import React from "react";
import {useNavigate} from "react-router-dom";
import {AxiosUserService} from "../../../services/implementation/AxiosUserService";
import useGlobalStore from "../../../store/store";
import * as lab9 from "lab9";
import VehicleType = lab9.lab9.common.vehicle.VehicleType;
import FuelType = lab9.lab9.common.vehicle.FuelType;
import * as Yup from "yup"
import {Button} from "flowbite-react";
import {Simulate} from "react-dom/test-utils";
import error = Simulate.error;

const CreateVehicleSchema = Yup.object().shape({
   name: Yup.string()
     .required('Required'),
    x: Yup.number()
        .min(-572)
        .required(),
    y: Yup.number(),
    enginePower: Yup.number().min(0),
 });

const CreateNewVehicleForm = () => {
    const {service} = useGlobalStore()
    const possibleVehicleTypes = VehicleType.values().map((it) => {
        return it.name
    })
    const possibleFuelTypes = FuelType.values().map((it) => {
        return it.name
    })
    const navigate = useNavigate()

    return (
        <Formik
            initialValues={{
                name: "",
                x: 0,
                y: 0,
                enginePower: 0.0,
                vehicleType: possibleVehicleTypes[0],
                fuelType: possibleFuelTypes[0],
            }}
            validationSchema={CreateVehicleSchema}
            onSubmit={
                (values, formikHelpers) => {
                    console.log("Submitted form!")
                    formikHelpers.setSubmitting(true)
                    service.createVehicle(
                        values.name,
                        values.x,
                        values.y,
                        values.enginePower,
                        values.vehicleType,
                        values.fuelType
                    ).then(r => {
                        console.log("Submitted form!")
                        console.log(r)
                        alert("Success!")
                        navigate("/vehicles")
                    }).catch(e => {
                        console.log(e)
                        formikHelpers.setErrors(e)
                    }).finally(() => {
                        formikHelpers.setSubmitting(false)
                    })
                }}>
            {(
                {
                    values,
                    handleSubmit,
                    handleChange,
                    handleBlur,
                    errors,
                    touched,
                    isSubmitting,
                }) => (
                <FormWrapper handleSubmit={handleSubmit} handleBlur={handleBlur}>
                    <FormInput
                        htmlName="name"
                        name={"Name"}
                        value={values.name}
                        type={"text"}
                        handleChange={handleChange}
                        errors={errors.name}
                        touched={touched.name}
                        required
                    />
                    <FormInput htmlName="x" name={"X"} value={values.x} type={"number"} handleChange={handleChange} errors={errors.x} touched={touched.x} required/>
                    <FormInput htmlName="y" name={"Y"} value={values.y} type={"number"} handleChange={handleChange} errors={errors.y} touched={touched.y} required/>
                    <FormSelect fieldName="vehicleType"
                                possibleValues={possibleVehicleTypes}
                                name={"Vehicle Type"}
                                value={values.vehicleType}
                                handleChange={handleChange}/>

                    <FormSelect fieldName="fuelType"
                                possibleValues={possibleFuelTypes}
                                name={"Fuel Type"}
                                value={values.fuelType}
                                handleChange={handleChange}/>
                    <div className="inline-flex justify-between w-full">
                        <Button type="submit" disabled={isSubmitting}>
                            Create
                        </Button>
                        <Button  color="gray" onClick={() => {
                            navigate("/vehicles")
                        }}>
                            {"Cancel"}
                        </Button>
                    </div>
                </FormWrapper>
            )}
        </Formik>
    )
}

export default CreateNewVehicleForm