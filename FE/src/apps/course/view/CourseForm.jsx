import { Dialog } from 'primereact/dialog';
import { Sidebar } from 'primereact/sidebar';
import { InputText } from 'primereact/inputtext';
import { InputNumber } from 'primereact/inputnumber';
import { Button } from 'primereact/button';
import { useEffect, useState } from 'react';

export default function CourseForm({
    visible,
    course,
    isDetail,
    isSidebar,
    onHide,
    onSubmit
}) {
    const [form, setForm] = useState({});

    useEffect(() => {
        if (course) {
            setForm(course);
        } else {
            setForm({
                title: '',
                description: '',
                price: 0,
                active: true
            });
        }
    }, [course]);

    const Container = isSidebar && !isDetail ? Sidebar : Dialog;

    return (
        <Container
            visible={visible}
            position={isSidebar && !isDetail ? 'right' : undefined}
            style={{ width: isSidebar ? '40rem' : '35rem' }}
            modal
            header={
                isDetail
                    ? 'Course Detail'
                    : course?.id
                        ? 'Update Course'
                        : 'Create Course'
            }
            onHide={onHide}
        >
            <div className="p-fluid">

                {/* TITLE */}
                <label className="font-bold">Title</label>
                {isDetail ? (
                    <div>{form.title}</div>
                ) : (
                    <InputText
                        value={form.title}
                        onChange={(e) =>
                            setForm({ ...form, title: e.target.value })
                        }
                    />
                )}

                {/* DESCRIPTION */}
                <label className="font-bold mt-3">Description</label>
                {isDetail ? (
                    <div>{form.description}</div>
                ) : (
                    <InputText
                        value={form.description}
                        onChange={(e) =>
                            setForm({ ...form, description: e.target.value })
                        }
                    />
                )}

                {/* PRICE */}
                <label className="font-bold mt-3">Price</label>
                {isDetail ? (
                    <div>{form.price}</div>
                ) : (
                    <InputNumber
                        value={form.price}
                        onValueChange={(e) =>
                            setForm({ ...form, price: e.value || 0 })
                        }
                    />
                )}

                {/* ACTION */}
                {!isDetail && (
                    <div className="flex justify-content-end gap-2 mt-4">
                        <Button
                            label="Cancel"
                            severity="secondary"
                            onClick={onHide}
                        />
                        <Button
                            label="Save"
                            icon="pi pi-save"
                            onClick={() => onSubmit(form)}
                        />
                    </div>
                )}
            </div>
        </Container>
    );
}
